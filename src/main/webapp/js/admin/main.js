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
/**
 * main for admin
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 */

/* main 相关操作 */
admin.main = {
};

/*
 * 注册到 admin 进行管理 
 */
var lastMain = -1;
admin.register.main =  {
    "obj": admin.main,
    "init": function () {
        admin.clearTip();
        admin.register.main.getLog();
        setInterval(admin.register.main.getLog, 5000);
    },
    "refresh": function () {
        admin.clearTip();
    },
    getLog: () => {
        $.ajax({
            url: Label.servePath + '/admin/logs',
            cache: false,
            timeout: 2000,
            success: function (result) {
                let json = result.result;
                for (let i = 0; i < json.length; i++) {
                    let r = json[i];
                    let rId = r.id;
                    if (rId > lastMain) {
                        let rDate = r.date;
                        let rLevel = r.level;
                        let rSrc = r.name + ':' + r.lineNumber;
                        let msg = r.message;
                        let buildStart = '<tbody class="table-oddRow"><tr class="table-hasExpend">';
                        rDate = rDate.substring(0, 19);
                        let build1 = '';
                        if (rLevel === 'WARN') {
                            build1 = '<td style="width: 40px; vertical-align: top"><span style="color: #f8ba0b; font-weight: bold">' + rLevel + '</span></td>';
                        } else if (rLevel === 'INFO') {
                            build1 = '<td style="width: 40px; vertical-align: top"><span style="color: #00bbff; font-weight: bold">' + rLevel + '</span></td>';
                        } else if (rLevel === 'ERROR') {
                            build1 = '<td style="width: 40px; vertical-align: top"><span style="color: #dd1144" font-weight: bold">' + rLevel + '</span></td>';
                        } else {
                            build1 = '<td style="width: 40px; vertical-align: top"><span style="color: #1ea0c3" font-weight: bold">' + rLevel + '</span></td>';
                        }
                        let build2 = '<td style="width: 135px; vertical-align: top"><span style="color: #4caf50; font-weight: bold">' + rDate + '</span></td>';
                        let build3 = '<td style="vertical-align: top"><span style="word-wrap: break-word; white-space: normal; word-break: break-all"><span style="color: #4caf50; font-weight: bold">' + rSrc + '</span><br>' + msg + '</span></td>';
                        let buildEnd = '</tr></tbody>';
                        let res = buildStart + build1 + build2 + build3 + buildEnd;
                        if (r.throwable !== undefined) {
                            res += r.throwable.class + ': ' + r.throwable.message + '<br>';
                            for (let j = 0; j < r.throwable.stackTrace.length; j++) {
                                res += r.throwable.stackTrace[j] + '<br>';
                            }
                        }
                        $('#recentLogs').prepend(res);
                        lastMain = rId;
                    }
                }
            }
        });
    },
};
