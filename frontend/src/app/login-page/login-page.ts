import { Component, effect, signal } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { authCodeFlowerConfig } from '../configs/app.auth';

@Component({
  selector: 'app-login-page',
  imports: [],
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss'
})
export class LoginPage {

  hasValidAccessToken = signal(false);

  constructor(private readonly oAuthService: OAuthService) {
    this.oAuthService.configure(authCodeFlowerConfig);
    this.oAuthService.loadDiscoveryDocumentAndTryLogin();

    effect(() => {
      this.hasValidAccessToken.set(this.oAuthService.hasValidAccessToken());
    });
  }

  login() {
    this.oAuthService.initLoginFlow();
  }

  logout() {
    this.oAuthService.logOut();
  }
}

