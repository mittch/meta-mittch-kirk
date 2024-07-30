inherit core-image
inherit populate_sdk_qt5

MACHINE_NAME ?= "${MACHINE}"
IMAGE_NAME = "${MACHINE_NAME}_${IMAGE_BASENAME}"

# Copy Licenses to image /usr/share/common-license
COPY_LIC_MANIFEST ?= "1"
COPY_LIC_DIRS ?= "1"

add_rootfs_version () {
    printf "${DISTRO_NAME} ${DISTRO_VERSION} (${DISTRO_CODENAME}) \\\n \\\l\n" > ${IMAGE_ROOTFS}/etc/issue
    printf "${DISTRO_NAME} ${DISTRO_VERSION} (${DISTRO_CODENAME}) %%h\n" > ${IMAGE_ROOTFS}/etc/issue.net
    printf "${IMAGE_NAME}\n\n" >> ${IMAGE_ROOTFS}/etc/issue
    printf "${IMAGE_NAME}\n\n" >> ${IMAGE_ROOTFS}/etc/issue.net
}
# add the rootfs version to the welcome banner
ROOTFS_POSTPROCESS_COMMAND += " add_rootfs_version;"

IMAGE_LINGUAS = "de-de"
#IMAGE_LINGUAS = "de-de fr-fr en-gb en-us pt-br es-es kn-in ml-in ta-in"

SUMMARY = "Image for Schauer display unit X from mittch"
DESCRIPTION = "Image for schauer display unit X"

LICENSE = "MIT"

#Prefix to the resulting deployable tarball name
export IMAGE_BASENAME = "sdux-mittch"

IMAGE_FEATURES += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston', \
       bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11-base', '', d), d)} \
"

# APP_LAUNCH_WAYLAND ?= "wayland-qtdemo-launch-cinematicexperience"
APP_LAUNCH_WAYLAND ?= "wayland-qtdemo-launch-analogclock"
APP_LAUNCH_WAYLAND:colibri-imx6ull ?= "wayland-qtdemo-launch-analogclock"
APP_LAUNCH_WAYLAND:colibri-imx6ull-emmc ?= "wayland-qtdemo-launch-analogclock"
APP_LAUNCH_WAYLAND:colibri-imx7 ?= "wayland-qtdemo-launch-analogclock"
APP_LAUNCH_WAYLAND:colibri-imx7-emmc ?= "wayland-qtdemo-launch-analogclock"
APP_LAUNCH_WAYLAND:verdin-am62 ?= "wayland-qtdemo-launch-analogclock"

APP_LAUNCH_X11 ?= "x-window-qtcinematicexperience"
APP_LAUNCH_X11:colibri-imx6ull ?= "x-window-analogclock"
APP_LAUNCH_X11:colibri-imx6ull-emmc ?= "x-window-analogclock"
APP_LAUNCH_X11:colibri-imx7 ?= "x-window-analogclock"
APP_LAUNCH_X11:colibri-imx7-emmc ?= "x-window-analogclock"
APP_LAUNCH_X11:verdin-am62 ?= "x-window-analogclock"

CONMANPKGS ?= "connman connman-plugin-loopback connman-plugin-ethernet connman-plugin-wifi connman-client"

IMAGE_INSTALL += " \
    packagegroup-boot \
    packagegroup-basic \
    packagegroup-base-tdx-cli \
    packagegroup-machine-tdx-cli \
    packagegroup-wifi-tdx-cli \
    packagegroup-wifi-fw-tdx-cli \
    udev-extraconf \
    ${CONMANPKGS} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'timestamp-service systemd-analyze', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', \
                         '${APP_LAUNCH_WAYLAND}', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', \
                         'weston-xwayland xterm', \
       bb.utils.contains('DISTRO_FEATURES', 'x11', '${APP_LAUNCH_X11}', '', d), d)} \
    ${@bb.utils.contains("MACHINE_FEATURES", "tpm2", "packagegroup-tpm2-tdx-cli", "",d)} \
    \
    packagegroup-tdx-cli \
    packagegroup-tdx-graphical \
    packagegroup-tdx-qt5 \
    packagegroup-fsl-isp \
    \
    bash \
    coreutils \
    less \
    makedevs \
    mime-support \
    net-tools \
    util-linux \
    v4l-utils \
    \
    gpicview \
    media-files \
"