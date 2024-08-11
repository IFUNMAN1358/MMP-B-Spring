import { createStore } from 'vuex';
import state from '@/store/state';
import mutations from '@/store/mutations';
import actions from '@/store/actions';
import getters from '@/store/getters';

export default createStore({
  state,
  mutations,
  actions,
  getters
});