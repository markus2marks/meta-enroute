SUMMARY = "This is the GUI for Hespect Software medical device"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "CLOSED"
SECTION = "base/app"

SRC_URI = "git://github.com/maplibre/maplibre-native-qt.git;protocol=https;branch=main \
"

SRCREV = "c51ca20c36f7156939b3f2442af2b1bbd008c5cc"


S = "${WORKDIR}/git"



inherit qt6-cmake systemd

DEPENDS = " \
    qtdeclarative-native \
    qtlocation \
    ccache-native \
"

EXTRA_OECMAKE	 = " \
. \
-G Ninja \
-DCMAKE_BUILD_TYPE="Release" \
-DCMAKE_C_COMPILER_LAUNCHER="ccache" \
-DCMAKE_CXX_COMPILER_LAUNCHER="ccache" \
-DCMAKE_INSTALL_PREFIX="${D}${exec_prefix}/lib/maplibre-native-qt" \
-DCMAKE_CXX_FLAGS="-Wno-error=shadow" \
  "

do_configure:prepend() {
  cd ${WORKDIR}/git
  git submodule update --init --recursive
}

do_compile() {
	cd ${WORKDIR}/git/
	ninja
}

do_install() {
	cd ${WORKDIR}/git
	ninja install
	# install -m 0755 -d ${D}${sysconfdir}/maplibre-native-qt
}

INSANE_SKIP:${PN} += "dev-so"
