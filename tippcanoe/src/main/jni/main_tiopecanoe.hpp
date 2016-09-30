struct index {
	long long start;
	long long end;
	unsigned long long index;
	short segment;
	unsigned long long seq : (64 - 16);  // pack with segment to stay in 32 bytes
};

void checkdisk(struct reader *r, int nreader);
int runMain(int argc, char **argv);
int render_vector_tiles(bool rewrite, bool rg, char *outdir, char *tmpdir, int layar_count, char **layars, int argc, char **argv);

extern int geometry_scale;
extern int quiet;

extern int CPUS;
extern int TEMP_FILES;
