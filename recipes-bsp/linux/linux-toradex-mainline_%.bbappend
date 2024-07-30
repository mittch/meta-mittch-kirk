FILESEXTRAPATHS:prepend := "${THISDIR}/linux-toradex-mainline-6:"

PR = "r1"

#
# the ts_resolution patch sets the reported ts size; the axes are flipped in the chip, so this is the place... 
#

SRC_URI += " \
	file://touchscreen.cfg \
	file://hy46xx_ts_devicetree.patch \
        "
