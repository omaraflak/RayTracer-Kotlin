all:
	ffmpeg -framerate 30 -i $(in)/image_%d.png -c copy $(in)/muxed.mkv
	ffmpeg -i $(in)/muxed.mkv \
		-c:v libx264 -crf 20 -profile:v main -pix_fmt yuv420p \
		-c:a aac -ac 2 -b:a 128k \
		-movflags faststart \
		$(in)/final.mp4