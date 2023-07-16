import { styled } from 'styled-components';
import Button from '../components/Button';
import Logo from '../components/Logo';

const Login = () => {
  return (
    <Container>
      <LogoContainer>
        <Logo fontSize="7xl" />
      </LogoContainer>
      <ButtonContainer>
        <KakaoLoginButton>
          <Button width="20rem" height="50px" color="yellow" border="none" fontWeight="600">
            <ButtonContent>
              <img src="/assets/kakao.svg" alt="카카오 로고" />
              <ButtonText>카카오 계정으로 로그인</ButtonText>
            </ButtonContent>
          </Button>
        </KakaoLoginButton>
        <GoogleLoginButton>
          <Button width="20rem" height="50px" color="white" border="solid" fontWeight="600">
            <ButtonContent>
              <img src="/assets/google.svg" alt="카카오 로고" />
              <ButtonText>구글 계정으로 로그인</ButtonText>
            </ButtonContent>
          </Button>
        </GoogleLoginButton>
      </ButtonContainer>
    </Container>
  );
};

export default Login;

const Container = styled.main`
  position: relative;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;

  width: 100%;
  height: 100%;
`;

const LogoContainer = styled.div``;

const ButtonContainer = styled.section`
  display: flex;
  flex-direction: column;
`;

const ButtonContent = styled.div`
  display: flex;
  align-items: center;
  padding: 12px 16px;
  padding: ${({ theme }) => theme.space[3]} ${({ theme }) => theme.space[4]};
`;

const ButtonText = styled.span`
  width: 100%;
`;

const KakaoLoginButton = styled.div`
  margin-bottom: ${({ theme }) => theme.space['3']};
`;

const GoogleLoginButton = styled.div``;
