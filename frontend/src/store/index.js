import Vue from 'vue'
import Vuex from 'vuex'
import http from '@/plugins/axios'

Vue.use(Vuex)

export default new Vuex.Store({
	state: {
	},
	mutations: {
	},
	actions: {
	},
	modules: {
    ui: {
			namespaced: true,
			state: {
				drawer: true,
        enabled: true
			},
			mutations: {
				setDrawer: (state, val) => state.drawer = val,
				setEnabled: (state, val) => state.enabled = val,
      }
    },
		references: {
			namespaced: true,
			state: {
				list: [],
				error: null,
				loading: false
			},
			mutations: {
				setLoading: (state, val) => state.loading = val,
				setError: (state, error) => state.error = error,
				setList: (state, list) => state.list = [...list],
			},
			actions: {
				fetch({ commit, state }) {
					if (state.loading) {
						return;
					}

					commit("setLoading", true);

					return http.get("references")
						.then((response) => {
							commit("setList", response.data.payload);
							commit("setError", null);
						})
						.catch((error) => {
							commit("setError", error);
						})
						.then(() => commit("setLoading", false));
				},
			}
		}
	}
});
