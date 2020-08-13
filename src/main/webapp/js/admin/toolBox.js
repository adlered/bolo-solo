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
 * preference for admin.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 */

/* tool-box 相关操作 */
admin.toolBox = {
  locale: '',
  /*
   * 初始化
   */
  init: function () {
    $('#loadMsg').text('');
  },
  /*
   * @description 参数校验
   */
  validate: function () {
    return true
  },
  /*
   * @description 更新
   */
  update: function () {
    alert("updated");
  }
}

/*
 * 注册到 admin 进行管理
 */
admin.register['tool-box'] = {
  'obj': admin.toolBox,
  'init': admin.toolBox.init,
  'refresh': function () {
    admin.clearTip()
  },
}
