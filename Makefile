all: create
	ffmpeg -framerate 30 -i output/$(anim)/images/image_%d.png -c copy output/$(anim)/videos/muxed.mkv
	ffmpeg -i output/$(anim)/videos/muxed.mkv \
		-c:v libx264 -crf 20 -profile:v main -pix_fmt yuv420p \
		-c:a aac -ac 2 -b:a 128k \
		-movflags faststart \
		output/$(anim)/videos/$(anim).mp4

create:
	mkdir -p output/$(anim)/videos
	mkdir -p output/$(anim)/images