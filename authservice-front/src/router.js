import { createRouter, createWebHistory } from 'vue-router';
import MainComponent from "@/components/MainComponent.vue";
import AuthComponent from "@/components/auth/AuthComponent.vue";
import RedirectHandler from "@/components/auth/RedirectHandler.vue";
import RefreshComponent from "@/components/RefreshComponent.vue";
import LogoutComponent from "@/components/auth/LogoutComponent.vue";

const routes = [

    {path: '/', component: MainComponent},
    {path: '/some', component: RefreshComponent}, ////////

    {path: '/auth', component: AuthComponent},
    {path: '/oauth/:provider/redirect', component: RedirectHandler},
    {path: '/logout', component: LogoutComponent}

];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;