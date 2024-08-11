import VueCookies from 'vue-cookies';

export function saveJwtTokens(accessToken, refreshToken) {
    VueCookies.set('accessToken', accessToken, { secure: true, httpOnly: true });
    VueCookies.set('refreshToken', refreshToken, { secure: true, httpOnly: true });
}

export function getJwtTokens() {
    const accessToken = VueCookies.get('accessToken');
    const refreshToken = VueCookies.get('refreshToken');
    return { accessToken, refreshToken };
}

export function getAccessToken() {
    return VueCookies.get('accessToken');
}

export function getRefreshToken() {
    return VueCookies.get('refreshToken');
}

export function deleteJwtTokens() {
    VueCookies.remove('accessToken');
    VueCookies.remove('refreshToken');
}