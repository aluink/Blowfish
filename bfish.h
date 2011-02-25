#ifndef __bfish_h__
#define __bfish_h__

typedef struct _BlowFish BlowFish;
typedef struct _BlowFish_Key BlowFish_Key;
typedef struct _BlowFish_Data BlowFish_Data;

BlowFish_Data *blowfish_encrypt(BlowFish_Key*,BlowFish_Data*);

#endif
