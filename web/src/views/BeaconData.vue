<template>
	<v-container fluid>
		<v-navigation-drawer width="384" v-model="fileInfoDrawer.open" app clipped right>
			<v-toolbar flat>
				<v-toolbar-title v-if="fileInfoDrawer.file != null" v-text="fileInfoDrawer.file.date"></v-toolbar-title>
				<v-spacer></v-spacer>
				<v-tooltip bottom>
					<template v-slot:activator="{ on, attrs }">
						<v-btn icon v-bind="attrs" v-on="on" @click.stop="fileInfoDrawer.open = false">
							<v-icon>mdi-close</v-icon>
						</v-btn>
					</template>
					<span>{{ $t("beacon-data.report.details.close") }}</span>
				</v-tooltip>
			</v-toolbar>

			<v-overlay absolute :value="fileInfoDrawer.open && fileInfoDrawer.confirm" opacity="0.80" @click.stop="confirmCancel()">
				<v-col align="center" justify="center">
					<v-alert type="warning">
						{{ $t("beacon-data.report.details.delete.confirm") }}
					</v-alert>
					<v-row align="center" justify="center">
						<v-row class="ma-1" no-gutters>
							<v-col cols="6">
								<v-btn style="width: 80%" color="error" @click.stop="confirmRemove()">
									{{ $t("beacon-data.report.details.delete.yes") }}
								</v-btn>
							</v-col>
							<v-col cols="6">
								<v-btn style="width: 80%" color="primary" @click.stop="confirmCancel()">
									{{ $t("beacon-data.report.details.delete.no") }}
								</v-btn>
							</v-col>
						</v-row>
					</v-row>
				</v-col>
			</v-overlay>

			<v-card flat outlined style="height: calc(100% - 64px);" v-if="fileInfoDrawer.open">
				<v-list dense>
					<v-overlay absolute :value="fileInfoDrawer.loading">
						<v-progress-circular indeterminate></v-progress-circular>
					</v-overlay>
					<div v-if="fileInfoDrawer.properties != null">
						<v-col v-for="(item, key) in fileInfoDrawer.properties" :key="key">
							<v-card class="pa-1" v-text="key" flat> </v-card>
							<v-card class="ml-4 pa-1" v-text="item" outlined> </v-card>
						</v-col>
					</div>
					<v-alert class="ma-4" type="error" v-if="fileInfoDrawer.properties == null && !fileInfoDrawer.loading">
						{{ $t("beacon-data.empty") }}
					</v-alert>
					<v-divider class="mt-4 mb-4"></v-divider>
					<v-list-item link v-if="fileInfoDrawer.file != null && fileInfoDrawer.properties != null" :href="'http://localhost:8080/beacon/reports/download/' + fileInfoDrawer.file.id">
						<v-list-item-action>
							<v-icon>mdi-download</v-icon>
						</v-list-item-action>
						<v-list-item-content>
							<v-list-item-title>
								{{ $t("beacon-data.report.details.download") }}
							</v-list-item-title>
						</v-list-item-content>
					</v-list-item>
					<v-list-item link v-if="!fileInfoDrawer.loading" @click.stop="fileInfoDrawer.confirm = true">
						<v-list-item-action>
							<v-icon>mdi-delete</v-icon>
						</v-list-item-action>
						<v-list-item-content>
							<v-list-item-title>
								{{ $t("beacon-data.report.details.delete") }}
							</v-list-item-title>
						</v-list-item-content>
					</v-list-item>
				</v-list>
			</v-card>
		</v-navigation-drawer>

		<v-card class="mx-auto">
			<v-toolbar dense flat>
				<v-toolbar-title>{{ $t("beacon-data.report") }}</v-toolbar-title>
				<v-spacer></v-spacer>
				<v-tooltip bottom>
					<template v-slot:activator="{ on, attrs }">
						<v-btn icon v-bind="attrs" v-on="on" @click.stop="refreshReportList()">
							<v-icon>mdi-refresh</v-icon>
						</v-btn>
					</template>
					<span>{{ $t("beacon-data.report.tooltip.refresh") }}</span>
				</v-tooltip>
				<v-tooltip bottom>
					<template v-slot:activator="{ on, attrs }">
						<v-btn icon v-bind="attrs" v-on="on" @click.stop="openForceReportDialog()">
							<v-icon>mdi-playlist-plus</v-icon>
						</v-btn>
					</template>
					<span>{{ $t("beacon-data.report.tooltip.force-report") }}</span>
				</v-tooltip>
			</v-toolbar>
			<v-divider></v-divider>
			<v-card class="mx-auto" v-if="reportList.items && reportList.items.length != 0">
				<v-list>
					<v-overlay absolute :value="reportList.loading">
						<v-progress-circular indeterminate></v-progress-circular>
					</v-overlay>
					<v-list-item v-for="report in reportList.items" :key="report.id" @click.stop="displayFileInfo(report)">
						<v-list-item-avatar>
							<v-icon>mdi-file</v-icon>
						</v-list-item-avatar>
						<v-list-item-content>
							<v-list-item-title v-text="report.displayName || report.filename"></v-list-item-title>
							<v-list-item-subtitle v-text="report.filename"></v-list-item-subtitle>
						</v-list-item-content>
						<v-list-item-action>
							<v-tooltip bottom v-if="report.atMidnight">
								<template v-slot:activator="{ on, attrs }">
									<v-icon v-bind="attrs" v-on="on">mdi-clock-check</v-icon>
								</template>
								<span>{{ $t("beacon-data.report.file.at-midnight") }}</span>
							</v-tooltip>
							<v-tooltip bottom v-else>
								<template v-slot:activator="{ on, attrs }">
									<v-icon v-bind="attrs" v-on="on">mdi-clock-alert</v-icon>
								</template>
								<span>{{ $t("beacon-data.report.file.not-at-midnight") }}</span>
							</v-tooltip>
						</v-list-item-action>
					</v-list-item>
				</v-list>
			</v-card>
			<v-card class="mx-auto" v-else>
				<v-alert type="info">
					{{ $t("beacon-data.report.empty") }}
				</v-alert>
			</v-card>
		</v-card>
		<v-dialog v-model="forceReportModal.open" :persistent="forceReportModal.loading || !!forceReportModal.last" max-width="600px">
			<v-card>
				<v-card-title>
					<span class="headline">{{ $t("beacon-data.report.force") }}</span>
				</v-card-title>
				<v-card-text v-if="!this.forceReportModal.last">
					<v-container>
						<v-col>
							<v-switch v-model="forceReportModal.fields.clearToo" :label="$t('beacon-data.report.force.fields.clear-too')" required></v-switch>
							<v-text-field v-model="forceReportModal.fields.displayName" :label="$t('beacon-data.report.force.fields.display-name')"></v-text-field>
						</v-col>
					</v-container>
				</v-card-text>
				<v-card-text v-else>
					<v-container>
						<v-alert v-if="forceReportModal.last != null && forceReportModal.last.created" type="success">
							{{ $t("beacon-data.report.force.success") }}
						</v-alert>
						<v-alert v-else type="error">
							{{ $t("beacon-data.report.force.error") }}
						</v-alert>
					</v-container>
				</v-card-text>
				<v-card-actions v-if="!this.forceReportModal.last">
					<v-spacer></v-spacer>
					<v-btn :disabled="forceReportModal.loading" @click.stop="forceReportModal.open = false">
						{{ $t("beacon-data.report.force.cancel") }}
					</v-btn>
					<v-btn :disabled="forceReportModal.loading" @click.stop="confirmForceReportDialog()">
						<v-progress-circular indeterminate color="primary" v-if="forceReportModal.loading"></v-progress-circular>
						<span :class="forceReportModal.loading ? 'ml-2' : ''">
							{{ $t("beacon-data.report.force.create") }}
						</span>
					</v-btn>
				</v-card-actions>
				<v-card-actions v-else>
					<v-spacer></v-spacer>
					<v-btn :disabled="forceReportModal.loading" @click.stop="resetForceReportDialog()">
						{{ $t("beacon-data.report.force.close") }}
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
	</v-container>
</template>

<script>
	export default {
		name: "BeaconData",
		methods: {
			refreshReportList() {
				this.reportList.loading = true;

				this.$http.get("http://localhost:8080/beacon/reports/all").then((response) => {
					this.reportList.items = response.data.reverse();
					this.reportList.loading = false;
				});
			},
			openForceReportDialog() {
				this.forceReportModal.open = true;
			},
			confirmForceReportDialog() {
				this.forceReportModal.loading = true;

				this.$http.get("http://localhost:8080/beacon/reports/force?clearToo=" + this.forceReportModal.fields.clearToo + "&displayName=" + (this.forceReportModal.fields.displayName || "")).then((response) => {
					this.forceReportModal.loading = false;
					this.forceReportModal.last = response.data;

					if (this.forceReportModal.last.created) {
						this.refreshReportList();
					}
				});
			},
			resetForceReportDialog() {
				this.forceReportModal.open = false;

				setTimeout(() => {
					this.forceReportModal.fields.clearToo = false;
					this.forceReportModal.fields.displayName = null;
					this.forceReportModal.last = null;
				}, 200);
			},
			displayFileInfo: function(file) {
				this.fileInfoDrawer.properties = null;

				if (!this.fileInfoDrawer.open || this.fileInfoDrawer.file == null || this.fileInfoDrawer.file.id != file.id) {
					this.fileInfoDrawer.file = file;
					this.fileInfoDrawer.open = true;
					this.fileInfoDrawer.loading = true;
					this.fileInfoDrawer.confirm = false;

					this.$http.get("http://localhost:8080/beacon/reports/property/" + file.id).then((response) => {
						this.fileInfoDrawer.loading = false;

						if (this.fileInfoDrawer.file == null || response.data.report.id == this.fileInfoDrawer.file.id) {
							this.fileInfoDrawer.properties = response.data.properties;
						}
					});
				} else {
					this.fileInfoDrawer.open = false;
				}
			},
			confirmRemove() {
				this.fileInfoDrawer.open = false;
				this.fileInfoDrawer.confirm = false;

				this.$http.get("http://localhost:8080/beacon/reports/delete/" + this.fileInfoDrawer.file.id).then((response) => {
					console.log(response);

					this.refreshReportList();
				});
			},
			confirmCancel() {
				this.fileInfoDrawer.confirm = false;
			},
		},
		mounted() {
			this.refreshReportList();
		},
		data: () => ({
			modal: true,
			reportList: {
				loading: true,
				items: [],
			},
			forceReportModal: {
				open: false,
				loading: false,
				last: null,
				fields: {
					clearToo: false,
					displayName: null,
				},
			},
			fileInfoDrawer: {
				open: false,
				loading: false,
				confirm: false,
				file: null,
				properties: null,
			},
		}),
	};
</script>
