<template>
	<v-container fill-height fluid>
		<v-row align="center" justify="center" class="fill-height">
			<v-img :src="currentSlideImage" contain aspect-ratio="0" class="fill-height" />
			<v-btn icon x-large style="position: absolute; left: 30px" @click="previous">
				<v-icon>mdi-chevron-left</v-icon>
			</v-btn>
			<v-btn icon x-large style="position: absolute; right: 30px" @click="next">
				<v-icon>mdi-chevron-right</v-icon>
			</v-btn>
		</v-row>

		<v-footer app class="pt-0 px-0">
			<v-col class="pa-0">
				<countdown v-if="currentSlide" :key="countdownKey" :time="currentSlideDuration * 1000" @end="onCountdownEnd">
					<template slot-scope="{ seconds }">
						<v-progress-linear :value="(1 - seconds / currentSlideDuration) * 100" />
					</template>
				</countdown>
				<v-row class="ma-0 px-3 pt-2">
					{{ current + 1 }} / {{ slideCount }} ({{ currentSlideDuration }}s)
					<v-spacer />
					{{ presentation.name }}
				</v-row>
			</v-col>
		</v-footer>
	</v-container>
</template>

<script>
export default {
	props: {
		presentation: {
			required: true,
			type: Object,
		},
		slides: {
			required: true,
			type: Array,
		},
	},
	data: () => ({
		current: null,
	}),
	computed: {
		slideCount() {
			return this.slides.length;
		},
		currentSlide() {
			const { current } = this;

			if (current != null) {
				return this.slides[current];
			}

			return null;
		},
		currentSlideImage() {
			const { currentSlide } = this;

			if (currentSlide != null) {
				return `/api/motd/slides/${currentSlide.id}/image`;
			}

			return null;
		},
		currentSlideDuration() {
			const { currentSlide, presentation } = this;

			return currentSlide?.duration || presentation.slideDuration;
		},
		countdownKey() {
			const {
				current,
				presentation: { id },
			} = this;
      
			return `${current}-${id}`;
		},
	},
	watch: {
		slideCount(val) {
			if (val == 0) {
				this.current = null;
			}
		},
		"presentation.id"() {
			this.current = 0;
		},
	},
	methods: {
		next() {
			this.to(+1);
		},
		previous() {
			this.to(-1);
		},
		to(offset) {
			const { slideCount } = this;

			if (slideCount == 0) {
				return;
			}

			if (this.current == null) {
				this.current = 0;
				return;
			}

			this.current += offset;

			if (this.current >= slideCount) {
				this.current = 0;
			} else if (this.current <= -1) {
				this.current = slideCount - 1;
			}
		},
		onCountdownEnd() {
			this.next();
		},
	},
	mounted() {
		this.next();
	},
};
</script>
