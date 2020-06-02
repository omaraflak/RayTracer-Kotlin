all: create
	ffmpeg -framerate 30 -i output/$(out)/images/image_%d.png -c copy output/$(out)/videos/muxed.mkv
	ffmpeg -i output/$(out)/videos/muxed.mkv \
		-c:v libx264 -crf 20 -profile:v main -pix_fmt yuv420p \
		-c:a aac -ac 2 -b:a 128k \
		-movflags faststart \
		output/$(out)/videos/$(out).mp4

create:
	mkdir -p output/$(out)/videos
	mkdir -p output/$(out)/images