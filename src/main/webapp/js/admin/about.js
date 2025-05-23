/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
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
/**
 *  about for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 * @author <a href="https://github.com/gakkiyomi">gakkiyomi (Bolo Commiter)</a>
 */

/* about 相关操作 */
admin.about = {
  init: async function () {
    const rawVersion = version.split(" ")[0].substr(1);
    const current = parseVersion(rawVersion);
    let latest = [...current];
    const checked = new Set();

    if (version.split(" ")[1] === "内测版") {
      $('#updateCheck').html('🍍 <font style="color: #3caf36">您正在使用菠萝博客内测版本，感谢您对开源的贡献！</font>');
      return;
    }

    function parseVersion (v) {
      return v.split('.').map(n => parseInt(n, 10));
    }

    function versionToStr (v) {
      return 'v' + v.join('.');
    }

    function versionToFilename (v) {
      return 'bolo_v' + v.join('_') + '_stable.zip';
    }

    function checkExists (v) {
      if (checked.has(versionToStr(v))) return Promise.resolve(false);
      checked.add(versionToStr(v));
      const url = 'https://ftp.stackoverflow.wiki/bolo/releases/' + versionToFilename(v);
      return new Promise(resolve => {
        $.ajax({
          url: url,
          type: 'HEAD',
          success: () => resolve(true),
          error: () => resolve(false),
        });
      });
    }

    function compareVersions (a, b) {
      const maxLen = Math.max(a.length, b.length);
      for (let i = 0; i < maxLen; i++) {
        const ai = a[i] || 0, bi = b[i] || 0;
        if (ai !== bi) return ai - bi;
      }
      return 0;
    }

    // 限制版本号最多3位
    function nextVersions (v) {
      const results = [];
      const len = v.length;

      for (let i = 0; i < len; i++) {
        let next = v.slice();
        next[i]++;
        for (let j = i + 1; j < len; j++) next[j] = 0;
        if (next.length <= 3) results.push(next);
      }

      if (len < 3) {
        let extended = v.slice();
        extended.push(0);
        for (let i = 0; i < extended.length; i++) {
          let next = extended.slice();
          next[i]++;
          for (let j = i + 1; j < extended.length; j++) next[j] = 0;
          if (next.length <= 3) results.push(next);
        }
      }

      return results;
    }

    async function findLatest () {
      const queue = [current];

      while (queue.length > 0) {
        const ver = queue.shift();

        const candidates = nextVersions(ver);
        for (const candidate of candidates) {
          const exists = await checkExists(candidate);
          if (exists && compareVersions(candidate, latest) > 0) {
            latest = candidate;
            queue.push(candidate);
          }
        }
      }
    }

    const currentExists = await checkExists(current);
    if (!currentExists) {
      $('#updateCheck').html('<font style="color: #e94c89">版本更新检查失败，网络错误或该版本为内测版本。</font>');
      return;
    }

    console.info('开始检测新版本...');
    await findLatest();

    if (compareVersions(current, latest) === 0) {
      $('#updateCheck').html('🍍 <font style="color: #3caf36">你正在使用菠萝博客最新版！</font>');
    } else {
      $('#updateCheck').html(
        `<a href="https://github.com/adlered/bolo-solo/releases" target="_blank" style="color: #991a1a">菠萝博客 ${versionToStr(latest)} 已推出，赶快更新吧！</a>`
      );
    }

    $("#tipMsg").text("");
    $("#loadMsg").text("");
  }
}


/*
 * 注册到 admin 进行管理 
 */
admin.register['about'] = {
  'obj': admin.about,
  'init': admin.about.init,
  'refresh': function () {
    admin.clearTip()
  }
};