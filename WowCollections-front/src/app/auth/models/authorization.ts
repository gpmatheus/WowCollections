export interface Authorization {
    access_token: string,
    token_type: string,
    expires_in: number,
    scope: string,
    sub: string,
    expiration: number,
    id_token: string
}
