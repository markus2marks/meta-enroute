SUMMARY = "This is the GUI for Hespect Software medical device"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "CLOSED"
SECTION = "base/app"

SRC_URI = "git://github.com/Akaflieg-Freiburg/enroute.git;protocol=https;branch=main \
           file://enroute-cmake-change.patch \
"

SRCREV = "fe6a7c16e0158efe0cd1dda7bc5cfc5924ca0b3d"
          


S = "${WORKDIR}/git"

inherit qt6-cmake

DEPENDS = "	\
    qtbase \
    qtlottie \
    qt3d \
    qtdeclarative \
    qtdeclarative-native \
    qt5compat \
    qttools \
    qttools-native \
    libxml2 \
    qtlocation \
    qthttpserver \
    qtpositioning \
    qtconnectivity \
    qtquick3d \
    qtquick3dphysics \
    qtsvg \
    qtlanguageserver \
    qtsensors \
    qtserialbus \
    qtserialport \
    qtcoap \
    qtscxml \
    dbus-glib  \
    qttranslations \
    qtspeech \
    qtwebview \
    maplibre-native-qt \
    libzip \
"

EXTRA_OECMAKE = "-DCMAKE_INSTALL_PREFIX="${D}${prefix}" \"

do_configure:prepend(){
	export QMapLibre_DIR=/usr/lib/maplibre-native-qt/lib/cmake/QMapLibre
	cd ${WORKDIR}/git
	git submodule update --init --recursive
}

do_compile() {
	cd ${WORKDIR}/git/
	cmake --build .
}

do_install() {
	cd ${WORKDIR}/git
	cmake --install .
}

FILES:${PN} += "/usr/share/icons \
                /usr/share/metainfo \
                /usr/share/icons/hicolor \
                /usr/share/icons/hicolor/scalable \
                /usr/share/icons/hicolor/scalable/apps \
"
