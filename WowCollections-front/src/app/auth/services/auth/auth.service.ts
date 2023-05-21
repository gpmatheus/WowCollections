import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Authorization } from '../../models/authorization';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly clientId: string = "930400512a0b414fab468ac8bf8102cc";
  readonly serverIp: string = '';
  readonly callbackURI: string = `http://${this.serverIp}:4200/auth/callback`;

  authorization!: Authorization;

  constructor(private httpClient: HttpClient) {}

  getRegistrationURI(): string {
    return `https://us.battle.net/oauth/authorize?`
      + `client_id=${this.clientId}&`
      + `redirect_uri=${this.callbackURI}&`
      + `scope=wow.profile&`
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
      })
    )
  }

  isAuthorizationValid(): boolean {
    if (!this.authorization) {
      return false;
    }
    return this.authorization.expiration > Math.floor(Date.now() / 1000)
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
