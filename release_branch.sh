#!/bin/bash

set -e

# 从 pom.xml 中提取 <version> 的内容
version=$(grep '<version>' pom.xml | head -n 1 | sed -E 's/.*<version>([^<]+)<\/version>.*/\1/')

# 检查 version 是否成功获取
if [ -z "$version" ]; then
  echo "❌ 无法从 pom.xml 中提取 version"
  exit 1
fi

# 将 version 中的 '-' 替换为 '_' 用于分支名
branch_name="${version//-/_}"

echo "✅ 提取版本号: $version"
echo "📦 分支名称将为: $branch_name"

# 确保当前在 master 分支
git checkout master || { echo "❌ 切换到 master 分支失败"; exit 1; }

# 拉取最新 master
git pull origin master

# 创建分支
git checkout -b "$branch_name"

# 显式推送分支，避免与 tag 名冲突
git push origin "refs/heads/$branch_name:refs/heads/$branch_name"

echo "✅ 分支 $branch_name 已创建并推送成功！"
