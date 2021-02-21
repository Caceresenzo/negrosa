import glob
import os
import re
import sys

import ezdxf
from ezdxf.addons.drawing import RenderContext, Frontend
from ezdxf.addons.drawing.matplotlib import MatplotlibBackend

import matplotlib.pyplot as plt

if len(sys.argv) != 3:
    print("usage: {} input output".format(sys.argv[0]))
    exit(1)

doc = ezdxf.readfile(sys.argv[1])
msp = doc.modelspace()

if len(doc.audit().errors) != 0:
    raise exception("The DXF document is damaged and can't be converted!")

fig = plt.figure()
ax = fig.add_axes([0, 0, 1, 1])
ctx = RenderContext(doc)
ctx.set_current_layout(msp)
ctx.current_layout.set_colors(bg='#FFFFFF')
out = MatplotlibBackend(ax)
Frontend(ctx, out).draw_layout(msp, finalize=True)

fig.savefig(sys.argv[2], dpi=300)
