package com.p3achb0t.api.wrappers.platforminfo

import com.p3achb0t.api.Context

class PlatformInfo(val ctx: Context) {

    var javaVendor: JavaVendor
        get() {
            return JavaVendor.valueOf(ctx.client.getPlatformInfo().getJavaVendorType()) ?: JavaVendor.Other
        }
        set(value) {
            ctx.client.getPlatformInfo().setJavaVendorType(value.value)
        }

    var osType: OSType
        get() {
            return OSType.valueOf(ctx.client.getPlatformInfo().getOsType()) ?: OSType.Other
        }
        set(value) {
            ctx.client.getPlatformInfo().setOsType(value.value)
        }

    var osVersion: OSVersion
        get() {
            return OSVersion.valueOf(ctx.client.getPlatformInfo().getOsVersion()) ?: OSVersion.Other
        }
        set(value) {
            ctx.client.getPlatformInfo().setOsVersion(value.value)
        }

    var memoryMaxMemoryMB: Int
        get() {
            return ctx.client.getPlatformInfo().getMaxMemoryMB()
        }
        set(value) {
            ctx.client.getPlatformInfo().setMaxMemoryMB(value)
        }
    var cpuCores: Int
        get() {
            return ctx.client.getPlatformInfo().getCpuCores()
        }
        set(value) {
            ctx.client.getPlatformInfo().setCpuCores(value)
        }

    var os64Bit: Boolean
        get() {
            return ctx.client.getPlatformInfo().getOs64Bit()
        }
        set(value) {
            ctx.client.getPlatformInfo().setOs64Bit(value)
        }

    var javaVersionMajor: Int
        get() {
            return ctx.client.getPlatformInfo().getJavaVersionMajor()
        }
        set(value) {
            ctx.client.getPlatformInfo().setJavaVersionMajor(value)
        }

    var javaVersionMinor: Int
        get() {
            return ctx.client.getPlatformInfo().getJavaVersionMinor()
        }
        set(value) {
            ctx.client.getPlatformInfo().setJavaVersionMinor(value)
        }
    var javaVersionPatch: Int
        get() {
            return ctx.client.getPlatformInfo().getJavaVersionPatch()
        }
        set(value) {
            ctx.client.getPlatformInfo().setJavaVersionPatch(value)
        }
}