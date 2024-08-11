<script>
import axios from "@/axios";
import { saveJwtTokens } from '@/js/token';
import { createSession } from '@/js/session/session';

export default {
  name: 'AuthComponent',
  data() {
    return {
      loginForm: {
        username: '',
        email: '',
        phoneNumber: '',
        password: ''
      },

      googleClientId: process.env.VUE_APP_OAUTH_GOOGLE_CLIENT_ID,
      googleRedirectUri: process.env.VUE_APP_OAUTH_GOOGLE_REDIRECT_URI,
      googleScope: process.env.VUE_APP_OAUTH_GOOGLE_SCOPE,

      facebookClientId: process.env.VUE_APP_OAUTH_FACEBOOK_CLIENT_ID,
      facebookRedirectUri: process.env.VUE_APP_OAUTH_FACEBOOK_REDIRECT_URI,
      facebookScope: process.env.VUE_APP_OAUTH_FACEBOOK_SCOPE,

      yandexClientId: process.env.VUE_APP_OAUTH_YANDEX_CLIENT_ID,
      yandexRedirectUri: process.env.VUE_APP_OAUTH_YANDEX_REDIRECT_URI
    }
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('/api/auth/login', this.loginForm);

        const accessToken = response.data.tokens.accessToken;
        const refreshToken = response.data.tokens.refreshToken;
        const userId = response.data.user.userId;

        saveJwtTokens(accessToken, refreshToken);
        await createSession(userId)

        console.log(response.data);
      } catch (error) {
        console.error('Login failed:', error);
      }
    },
    loginWithGoogle() {
      window.location.href =
        `https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${this.googleClientId}&redirect_uri=${this.googleRedirectUri}&scope=${this.googleScope}`;
    },
    loginWithFacebook() {
      window.location.href =
        `https://www.facebook.com/v20.0/dialog/oauth?client_id=${this.facebookClientId}&redirect_uri=${this.facebookRedirectUri}&scope=${this.facebookScope}&response_type=code`;
    },
    loginWithYandex() {
      window.location.href =
        `https://oauth.yandex.ru/authorize?response_type=code&client_id=${this.yandexClientId}&redirect_uri=${this.yandexRedirectUri}`;
    }
  }
};
</script>

<template>
   <div>
    <form @submit.prevent="login">
      <div>
        <label for="username">Username</label>
        <input type="text" id="username" v-model="loginForm.username">
      </div>
      <div>
        <label for="email">Email</label>
        <input type="email" id="email" v-model="loginForm.email">
      </div>
      <div>
        <label for="phoneNumber">Phone Number</label>
        <input type="tel" id="phoneNumber" v-model="loginForm.phoneNumber">
      </div>
      <div>
        <label for="password">Password</label>
        <input type="password" id="password" v-model="loginForm.password" required>
      </div>
      <button type="submit">Login</button>
    </form>

    <div>
      <button @click="loginWithGoogle">Login with Google</button>
      <button @click="loginWithFacebook">Login with Facebook</button>
      <button @click="loginWithYandex">Login with Yandex</button>
    </div>
  </div>
</template>