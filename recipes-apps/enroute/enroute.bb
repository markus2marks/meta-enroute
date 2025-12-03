SUMMARY = "Enroute Flight Navigation a mobile flight navigation app "
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "CLOSED"
SECTION = "base/app"

SRC_URI = "git://github.com/Akaflieg-Freiburg/enroute.git;protocol=https;branch=main \
           file://enroute-cmake-change.patch \
           file://enroute.service \
"

SRCREV = "022dacbe37189a203c963d3b01fdfc1fdf164466"
          


S = "${WORKDIR}/git"

inherit qt6-cmake

DEPENDS = "	\
    qtbase \
    qt3d \
    qtlottie \
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
	install -m 0755 -d ${D}${systemd_unitdir}/system
    	install -m 0644 ${WORKDIR}/enroute.service ${D}${systemd_unitdir}/system/
}

FILES:${PN} += "/usr/share/icons \
                /usr/share/metainfo \
                /usr/share/icons/hicolor \
                /usr/share/icons/hicolor/scalable \
                /usr/share/icons/hicolor/scalable/apps \
                /usr/lib \
  		/usr/lib/systemd \
  		/usr/lib/systemd/system \
"

SYSTEMD_SERVICE:${PN} += "enroute.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
