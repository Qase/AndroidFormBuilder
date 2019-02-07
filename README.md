<!-- 
[![Build Status](https://travis-ci.org/Qase/KotlinLogger.svg?branch=master)](https://travis-ci.org/Qase/KotlinLogger)
-->

[![Release](https://jitpack.io/v/Qase/AndroidFormBuilder.svg)](https://jitpack.io/#Qase/AndroidFormBuilder)
[![Build Status](https://travis-ci.org/Qase/AndroidFormBuilder.svg?branch=master)](https://travis-ci.org/Qase/AndroidFormBuilder)
[![codebeat badge](https://codebeat.co/badges/06a38cfa-46ff-4019-9e71-eef48bae8f0f)](https://codebeat.co/projects/github-com-qase-androidformbuilder-master)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Maintainer: kidal5](https://img.shields.io/badge/Maintainer-balakz-blue.svg)](mailto:balakz@quanti.cz)
[![Qase: AndroidFormBuilder](https://img.shields.io/badge/Qase-AndroidFormBuilder-ff69b4.svg)](https://github.com/Qase/AndroidFormBuilder)


## AndroidFormBuilder

Easy to use android form builder. 

## Features
* Very easy to use
* Skinable
* Lot of beautiful color variants
* Easily extendable to new elements
* Built-in input data validating
* Sample [app](github/red.png) is ready to build

## Installation

Click [HERE](https://jitpack.io/#Qase/AndroidFormBuilder).

## Code example

Usage is simple

```kotlin
FormBuilder().
                apply{
                    //static elements
                    addElement(HeaderElement("Static elements"), true)
                    addElement(TextElement("TextElement value"), true)
                    addElement(OpenableHeaderTextElement("OpenableHeaderTextElement label", longerStringValues))
                    addSpace()
                    addElement(HeaderElement("Input elements"), true)
                    addElement(LabelSwitchElement("LabelSwitchElement label", true, showToastCheckboxCallback), true)
                    addSpace()
                    addElement(HeaderElement("Input validable elements"), true)
                    addElement(ActionElement(validateActionCallback, "Validate all elements"))
                    addSpace()
                }
                .buildForm(context, parentView, FormStyleBundle.colorBundleTwo())

```

## Future development
* new layouts
* more color variants
* send your requests

<img src="github/red.png" width="250">

## License
[MIT](https://github.com/nishanths/license/blob/master/LICENSE)
