#### from the original bbappend:
# require recipes-bsp/u-boot/u-boot-toradex-env.inc
# include conf/tdx_version.conf

# LOCALVERSION = "-${TDX_VER_ITEM}"

# SRCREV = "aca804c9ddadbf34a8ff82779e5598ec5e319f23"
# SRCBRANCH = "2016.11-toradex"
# SRCREV_use-head-next = "${AUTOREV}"
# SRCBRANCH_use-head-next = "2016.11-toradex-next"
#### /end

PR = "r1"

COMPATIBLE_MACHINE = "mx6"
FILESEXTRAPATHS:prepend := "${THISDIR}/u-boot-toradex-2022:"

SRC_URI += " file://gpios.patch;patch=1 \
	file://display.patch;patch=1 \
	"

