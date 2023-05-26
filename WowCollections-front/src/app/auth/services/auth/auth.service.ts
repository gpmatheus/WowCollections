import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Authorization } from '../../models/authorization';
import { Observable, tap } from 'rxjs';
import { User } from '../../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly clientId: string = "930400512a0b414fab468ac8bf8102cc";

  // place your public ip addess here
  readonly serverIp: string = '192.168.1.107';

  readonly callbackURI: string = `http://${this.serverIp}:4200/auth/callback`;

  authorization!: Authorization;
  user!: User;

  constructor(private httpClient: HttpClient) {}

  getRegistrationURI(): string {
    return `https://us.battle.net/oauth/authorize?`
      + `client_id=${this.clientId}&`
      + `redirect_uri=${this.callbackURI}&`
      + `scope=wow.profile openid&`
      + `response_type=code&`
      + `response_mode=fragment&`
      + `state=${this.getState()}&`
      + `nonce=${this.getNonce()}`
  }

  authenticate(code: string): Observable<Authorization> {
    let params = new HttpParams()
      .append('code', code)
      .append('scope', 'wow.profile')
      .append('redirect_uri', this.callbackURI)
      .append('grant_type', 'authorization_code');
    return this.httpClient.post<Authorization>("http://localhost:8080/authorization", null, { params }).pipe(
      tap((auth: Authorization) => {
        auth.expiration = Math.floor(Date.now() / 1000) + auth.expires_in;
        this.authorization = auth;
        const jwtSections = auth.id_token.split('.');
        const payload = jwtSections[1];
        const decodedPayload = JSON.parse(window.atob(payload));
        this.user = { battleTag: decodedPayload['battle_tag'] };
      })
    )
  }

  isAuthorizationValid(): boolean {
    if (!this.authorization) {
      return false;
    }
    return this.authorization.expiration > Math.floor(Date.now() / 1000)
  }

  get getUser(): User {
    return this.user;
  }

  getToken(): string | null {
    return this.authorization ? this.authorization.access_token : null;
  }

  private getState(): string {
    return "foobar";
  }

  private getNonce(): string {
    return "barfoo";
  }
}
