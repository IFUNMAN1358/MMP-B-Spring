<template>
  <div></div>
</template>

<script>
import axios from "@/axios";
import {saveJwtTokens} from "@/js/token";
import {createSession} from "@/js/session/session";

export default {
  name: "RedirectHandler",

  data() {
    return {
      providerName: '',
    };
  },

  created() {
    if (window.location.pathname.includes('google')) {
      this.providerName = 'google';
    } else if (window.location.pathname.includes('facebook')) {
      this.providerName = 'facebook';
    } else if (window.location.pathname.includes('yandex')) {
      this.providerName = 'yandex';
    }

    const code = this.handleRedirect();
    if (code) {
      this.sendCode(code, this.providerName);
    }
  },
  methods: {
    async sendCode(code, providerName) {
      try {
        const response = await axios.post(`/api/auth/${providerName}/code`, null, {
          params: { code }
        });

        const accessToken = response.data.tokens.accessToken;
        const refreshToken = response.data.tokens.refreshToken;
        const userId = response.data.user.userId;

        saveJwtTokens(accessToken, refreshToken);
        await createSession(userId)

        console.log(response.data);
        return response;
      } catch (error) {
        console.error('Error sending code:', error);
        throw error;
      }
    },
    handleRedirect() {
      const urlParams = new URLSearchParams(window.location.search);
      const code = urlParams.get('code');
      if (code) {
        return code;
      } else {
        console.error('No code found in URL');
      }
    },
  }
};
</script>