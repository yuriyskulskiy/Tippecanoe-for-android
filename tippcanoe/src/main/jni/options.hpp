#define A_COALESCE ((int) 'c')
#define A_REVERSE ((int) 'r')
#define A_REORDER ((int) 'o')
#define A_LINE_DROP ((int) 'l')
#define A_DEBUG_POLYGON ((int) 'd')
#define A_POLYGON_DROP ((int) 'p')
#define A_PREFER_RADIX_SORT ((int) 'R')
#define A_CALCULATE_FEATURE_DENSITY ((int) 'g')

#define P_SIMPLIFY ((int) 's')
#define P_SIMPLIFY_LOW ((int) 'S')
#define P_FEATURE_LIMIT ((int) 'f')
#define P_KILOBYTE_LIMIT ((int) 'k')
#define P_DYNAMIC_DROP ((int) 'd')
#define P_INPUT_ORDER ((int) 'i')
#define P_POLYGON_SPLIT ((int) 'p')
#define P_CLIPPING ((int) 'c')
#define P_DUPLICATION ((int) 'D')

extern int prevent[256];
extern int additional[256];
