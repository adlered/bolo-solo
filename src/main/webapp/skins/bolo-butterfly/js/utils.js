/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020-present, https://github.com/bolo-blog
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
function debounce (func, wait, immediate) {
  var timeout
  return function () {
    var context = this
    var args = arguments
    var later = function () {
      timeout = null
      if (!immediate) func.apply(context, args)
    }
    var callNow = immediate && !timeout
    clearTimeout(timeout)
    timeout = setTimeout(later, wait)
    if (callNow) func.apply(context, args)
  }
};

function throttle (func, wait, options) {
  var timeout, context, args
  var previous = 0
  if (!options) options = {}

  var later = function () {
    previous = options.leading === false ? 0 : new Date().getTime()
    timeout = null
    func.apply(context, args)
    if (!timeout) context = args = null
  }

  var throttled = function () {
    var now = new Date().getTime()
    if (!previous && options.leading === false) previous = now
    var remaining = wait - (now - previous)
    context = this
    args = arguments
    if (remaining <= 0 || remaining > wait) {
      if (timeout) {
        clearTimeout(timeout)
        timeout = null
      }
      previous = now
      func.apply(context, args)
      if (!timeout) context = args = null
    } else if (!timeout && options.trailing !== false) {
      timeout = setTimeout(later, remaining)
    }
  }

  return throttled
}

function sidebarPaddingR () {
  var innerWidth = window.innerWidth
  var clientWidth = document.body.clientWidth
  var paddingRight = innerWidth - clientWidth
  if (innerWidth !== clientWidth) {
    $('body').css('padding-right', paddingRight)
  }
}

// iPadOS
function isIpad () {
  return navigator.platform === 'MacIntel' && navigator.maxTouchPoints > 1
}

function isTMobile () {
  var ua = navigator.userAgent
  var pa = /iPad|iPhone|iPod|Android|Opera Mini|BlackBerry|webOS|UCWEB|Blazer|PSP|IEMobile|Symbian/g
  return window.screen.width < 992 && pa.test(ua)
}

function isMobile () {
  return this.isIpad() || this.isTMobile()
}

function isDesktop () {
  return !this.isMobile()
}

function scrollToDest (name, offset = 0) {
  var scrollOffset = $(name).offset()
  $('body,html').animate({
    scrollTop: scrollOffset.top - offset
  })
};

function loadScript (url, callback) {
  var script = document.createElement('script')
  script.type = 'text/javascript'
  if (script.readyState) { // IE
    script.onreadystatechange = function () {
      if (script.readyState === 'loaded' ||
        script.readyState === 'complete') {
        script.onreadystatechange = null
        callback()
      }
    }
  } else { // Others
    script.onload = function () {
      callback()
    }
  }
  script.src = url
  document.body.appendChild(script)
};

function snackbarShow (text, showAction, duration) {
  var a = (typeof showAction !== 'undefined') ? showAction : false
  var d = (typeof duration !== 'undefined') ? duration : 2000
  var position = GLOBAL_CONFIG.Snackbar.position
  var bg = document.documentElement.getAttribute('data-theme') === 'light' ? GLOBAL_CONFIG.Snackbar.bgLight : GLOBAL_CONFIG.Snackbar.bgDark
  Snackbar.show({
    text: text,
    backgroundColor: bg,
    showAction: a,
    duration: d,
    pos: position
  })
}

const Cookies = {
  get: function (name) {
    const value = `; ${document.cookie}`
    const parts = value.split(`; ${name}=`)
    if (parts.length === 2) return parts.pop().split(';').shift()
  },
  set: function (name, value, days) {
    var expires = ''
    if (days) {
      var date = new Date()
      date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000))
      expires = '; expires=' + date.toUTCString()
    }
    document.cookie = name + '=' + (value || '') + expires + '; path=/'
  }
}

/**
 * lazyload
 */
if (GLOBAL_CONFIG.islazyload) {
  window.lazyLoadOptions = {
    elements_selector: 'img',
    threshold: 0
  }
}
