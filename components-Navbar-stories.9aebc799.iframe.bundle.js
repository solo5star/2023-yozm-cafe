/*! For license information please see components-Navbar-stories.9aebc799.iframe.bundle.js.LICENSE.txt */
"use strict";(self.webpackChunkyozm_cafe=self.webpackChunkyozm_cafe||[]).push([[425],{"./src/components/Navbar.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Default:()=>Default,__namedExportsOrder:()=>__namedExportsOrder,default:()=>Navbar_stories});var index_esm=__webpack_require__("./node_modules/react-icons/pi/index.esm.js"),dist=__webpack_require__("./node_modules/react-router/dist/index.js"),react=__webpack_require__("./node_modules/react/index.js"),react_namespaceObject=__webpack_require__.t(react,2),router=__webpack_require__("./node_modules/@remix-run/router/dist/router.js");function _extends(){return _extends=Object.assign?Object.assign.bind():function(target){for(var i=1;i<arguments.length;i++){var source=arguments[i];for(var key in source)Object.prototype.hasOwnProperty.call(source,key)&&(target[key]=source[key])}return target},_extends.apply(this,arguments)}function _objectWithoutPropertiesLoose(source,excluded){if(null==source)return{};var key,i,target={},sourceKeys=Object.keys(source);for(i=0;i<sourceKeys.length;i++)key=sourceKeys[i],excluded.indexOf(key)>=0||(target[key]=source[key]);return target}new Set(["application/x-www-form-urlencoded","multipart/form-data","text/plain"]);const _excluded=["onClick","relative","reloadDocument","replace","state","target","to","preventScrollReset"];react_namespaceObject.startTransition;const isBrowser="undefined"!=typeof window&&void 0!==window.document&&void 0!==window.document.createElement,ABSOLUTE_URL_REGEX=/^(?:[a-z][a-z0-9+.-]*:|\/\/)/i,Link=react.forwardRef((function LinkWithRef(_ref4,ref){let absoluteHref,{onClick,relative,reloadDocument,replace,state,target,to,preventScrollReset}=_ref4,rest=_objectWithoutPropertiesLoose(_ref4,_excluded),{basename}=react.useContext(dist.Us),isExternal=!1;if("string"==typeof to&&ABSOLUTE_URL_REGEX.test(to)&&(absoluteHref=to,isBrowser))try{let currentUrl=new URL(window.location.href),targetUrl=to.startsWith("//")?new URL(currentUrl.protocol+to):new URL(to),path=(0,router.Zn)(targetUrl.pathname,basename);targetUrl.origin===currentUrl.origin&&null!=path?to=path+targetUrl.search+targetUrl.hash:isExternal=!0}catch(e){}let href=(0,dist.oQ)(to,{relative}),internalOnClick=function useLinkClickHandler(to,_temp){let{target,replace:replaceProp,state,preventScrollReset,relative}=void 0===_temp?{}:_temp,navigate=(0,dist.s0)(),location=(0,dist.TH)(),path=(0,dist.WU)(to,{relative});return react.useCallback((event=>{if(function shouldProcessLinkClick(event,target){return!(0!==event.button||target&&"_self"!==target||function isModifiedEvent(event){return!!(event.metaKey||event.altKey||event.ctrlKey||event.shiftKey)}(event))}(event,target)){event.preventDefault();let replace=void 0!==replaceProp?replaceProp:(0,router.Ep)(location)===(0,router.Ep)(path);navigate(to,{replace,state,preventScrollReset,relative})}}),[location,navigate,path,replaceProp,state,target,to,preventScrollReset,relative])}(to,{replace,state,target,preventScrollReset,relative});return react.createElement("a",_extends({},rest,{href:absoluteHref||href,onClick:isExternal||reloadDocument?onClick:function handleClick(event){onClick&&onClick(event),event.defaultPrevented||internalOnClick(event)},ref,target}))}));var DataRouterHook,DataRouterStateHook;(function(DataRouterHook){DataRouterHook.UseScrollRestoration="useScrollRestoration",DataRouterHook.UseSubmit="useSubmit",DataRouterHook.UseSubmitFetcher="useSubmitFetcher",DataRouterHook.UseFetcher="useFetcher"})(DataRouterHook||(DataRouterHook={})),function(DataRouterStateHook){DataRouterStateHook.UseFetchers="useFetchers",DataRouterStateHook.UseScrollRestoration="useScrollRestoration"}(DataRouterStateHook||(DataRouterStateHook={}));var styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const Navbar=()=>{const{pathname}=(0,dist.TH)();return(0,jsx_runtime.jsxs)(Container,{children:[(0,jsx_runtime.jsxs)(IconContainer,{to:"/",$isActive:"/"===pathname,children:[(0,jsx_runtime.jsx)(Icon,{as:index_esm.u6N}),(0,jsx_runtime.jsx)(IconName,{children:"홈"})]}),(0,jsx_runtime.jsxs)(IconContainer,{to:"/login",$isActive:"/login"===pathname,children:[(0,jsx_runtime.jsx)(Icon,{as:index_esm.VDT}),(0,jsx_runtime.jsx)(IconName,{children:"로그인"})]})]})};Navbar.displayName="Navbar";const components_Navbar=Navbar,IconName=styled_components_browser_esm.zo.span`
  font-size: small;
`,IconContainer=(0,styled_components_browser_esm.zo)(Link)`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;

  color: ${({$isActive,theme})=>$isActive?theme.color.primary:theme.color.text.secondary};
  text-decoration: none;
`,Container=styled_components_browser_esm.zo.nav`
  display: flex;
  justify-content: space-evenly;

  width: 100%;
  height: 72px;

  background-color: ${({theme})=>theme.color.white};
`,Icon=styled_components_browser_esm.zo.div`
  font-size: ${({theme})=>theme.fontSize["4xl"]};
`,Navbar_stories={title:"Navbar",component:components_Navbar},Default={args:{}};Default.parameters={...Default.parameters,docs:{...Default.parameters?.docs,source:{originalSource:"{\n  args: {}\n}",...Default.parameters?.docs?.source}}};const __namedExportsOrder=["Default"]}}]);
//# sourceMappingURL=components-Navbar-stories.9aebc799.iframe.bundle.js.map