<template>
	<v-container fluid>
		<v-card class="mx-auto">
			<v-toolbar dense flat>
				<v-toolbar-title>OVERVIEW</v-toolbar-title>

				<v-spacer></v-spacer>

				<v-tooltip bottom>
					<template v-slot:activator="{ on, attrs }">
						<v-btn icon v-bind="attrs" v-on="on" @click.stop="refreshInfoList()">
							<v-icon>mdi-refresh</v-icon>
						</v-btn>
					</template>
					<span>Refresh</span>
				</v-tooltip>
			</v-toolbar>
			<v-divider></v-divider>
			<v-row class="mb-6" no-gutters>
				<v-col class="ma-6">
					<v-card class="pa-2" outlined tile>
						<v-card-text>
							<div>{{ $t("home.overview.stats.beacon-data") }}</div>
							<p class="display-1 text--primary">
								{{ $tc("home.overview.stats.beacon-data.entry", info.beacon ? info.beacon.count : 0, { count: info.beacon ? info.beacon.count : 0 }) }}
							</p>
						</v-card-text>
						<v-card-actions>
							<v-spacer></v-spacer>
							<v-btn outlined color="primary accent-4" to="/beacon-data">
								{{ $t('home.overview.stats.beacon-data.more') }}
							</v-btn>
						</v-card-actions>
					</v-card>
				</v-col>
			</v-row>
		</v-card>
	</v-container>
</template>

<script>
	export default {
		name: "Home",
		methods: {
			refreshInfoList: function() {
				this.$http.get("http://localhost:8080/beacon/line/status").then((response) => {
					this.info.beacon = response.data;
				});
			},
		},
		mounted() {
			this.refreshInfoList();
		},
		data: () => ({
			info: {
				beacon: null,
			},
		}),
	};
</script>
