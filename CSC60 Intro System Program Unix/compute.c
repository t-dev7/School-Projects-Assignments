/* Trevor Davis */
/* lab5         */

#include "lab5.h"

void compute(double radius, double *area, double *cir)
{
    *area = M_PI * ((radius) * (radius));
    *cir = 2 * M_PI * radius;

    return;
}
