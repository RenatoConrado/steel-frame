import { AuthConfig } from 'angular-oauth2-oidc';

export const authCodeFlowerConfig: AuthConfig = {
  issuer: 'http://localhost:8080',
  redirectUri: window.location.origin + '/index.html',
  clientId: 'angular-client',
  responseType: 'code',
  scope: 'openid profile offline_access',
  showDebugInformation: true,
  strictDiscoveryDocumentValidation: false,
  requireHttps: false
};
