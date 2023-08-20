import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.buildSteps.SSHUpload
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.buildSteps.sshUpload
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCompose
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.buildSteps.sshExec
import jetbrains.buildServer.configs.kotlin.buildSteps.sshUpload
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

version = "2023.05"

project {
    description = "yozm.cafe 프로젝트의 CI/CD 파이프라인 스크립트입니다"

    buildType(Server)
    buildType(Client)
}

object Server : BuildType({
    name = "Server"

    vcs {
        root(DslContext.settingsRoot)
        cleanCheckout = true
    }

    params {
        param("BuildMode", "dev")
        param("DeployTargetUrl", "%DevDeployTargetUrl%")
    }

    steps {
        script {
            name = "개발 서버용 Profile 생성"
            workingDir = "server/src/main/resources"
            scriptContent = """echo "%env.APPLICATION_DEV%" >> application-dev.properties"""
        }
        dockerCompose {
            name = "MySQL 설정"
            file = "server/docker-compose.yml"
        }
        gradle {
            name = "빌드"
            tasks = "clean build"
            buildFile = "server/build.gradle"
            gradleParams = "-Pprofile=%BuildMode%"
            gradleWrapperPath = "server"
        }
        sshUpload {
            name = "빌드 파일 업로드"
            transportProtocol = SSHUpload.TransportProtocol.SCP
            sourcePath = "server/build/libs/yozm-cafe-0.0.1-SNAPSHOT.jar"
            targetUrl = DslContext.getParameter("env.DEPLOY_TARGET_URL")
            authMethod = sshAgent {
                username = "ubuntu"
            }
        }
        sshExec {
            name = "배포"
            commands = """
                if sudo lsof -i :8080; then
                    echo "Port 8080 is already in use. Killing the process..."
                    sudo lsof -i :8080 | awk 'NR!=1 {print ${'$'}2}' | sudo xargs kill -9
                fi
                
                nohup java -jar yozm-cafe-0.0.1-SNAPSHOT.jar > nohup.out 2> nohup.err < /dev/null &
            """.trimIndent()
            targetUrl = "%DeployTargetUrl%"
            authMethod = sshAgent {
                username = "ubuntu"
            }
        }
    }

    triggers {
        vcs {
            triggerRules = "+:/server"
            branchFilter = "+:main"
            buildParams {
                param("BuildMode", "prod")
                param("DeployTargetUrl", "%ProdDeployTargetUrl%")
            }
        }
        vcs {
            triggerRules = "+:/server"
            branchFilter = "+:dev"
            buildParams {
                param("BuildMode", "dev")
                param("DeployTargetUrl", "%DevDeployTargetUrl%")
            }
        }
    }

    features {
        perfmon {
        }
        sshAgent {
            teamcitySshKey = "yozm-cafe.pem"
        }
    }
})

object Client : BuildType({
    name = "Client"

    vcs {
        root(DslContext.settingsRoot)
        cleanCheckout = true
    }

    params {
        param("BuildMode", "dev")
        param("DeployTargetUrl", "%DevDeployTargetUrl%")
    }

    steps {
        nodeJS {
            name = "Install dependencies and build"
            workingDir = "client"
            shellScript = """
                yarn install --frozen-lockfile
                yarn build
            """.trimIndent()
        }
        sshUpload {
            name = "Deploy build files"
            transportProtocol = SSHUpload.TransportProtocol.SCP
            sourcePath = "client/dist/**"
            targetUrl = "%DeployTargetUrl%:public"
            authMethod = sshAgent {
                username = "ubuntu"
            }
        }
    }

    triggers {
        vcs {
            triggerRules = "+:/client"
            branchFilter = "+:main"
            buildParams {
                param("BuildMode", "prod")
                param("DeployTargetUrl", "%ProdDeployTargetUrl%")
            }
        }
        vcs {
            triggerRules = "+:/client"
            branchFilter = "+:dev"
            buildParams {
                param("BuildMode", "dev")
                param("DeployTargetUrl", "%DevDeployTargetUrl%")
            }
        }
    }

    features {
        perfmon {
        }
        sshAgent {
            teamcitySshKey = "yozm-cafe.pem"
        }
    }
})
