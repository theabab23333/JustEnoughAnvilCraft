# JustEnoughAnvilCraft

[简体中文](readme.md) || [English](readme.en.md)  
[![CI for Mod](https://github.com/theabab23333/JustEnoughAnvilCraft/actions/workflows/ci.yml/badge.svg)](https://github.com/theabab23333/JustEnoughAnvilCraft/actions/workflows/ci.yml)  
[![Pull Request Check](https://github.com/theabab23333/JustEnoughAnvilCraft/actions/workflows/pull_request.yml/badge.svg)](https://github.com/theabab23333/JustEnoughAnvilCraft/actions/workflows/pull_request.yml)

 ## JustEnoughAnvilCraft是[AnvilCraft](https://github.com/AnvilCraft/AnvilCraft)的附属, 用于增强AnvilCraft在JEI中的显示效果

 > 本体配方在JEI中的缺点:  
- 在含有方块参与的配方中无法查看输入输出的方块的合成配方或用途(那只是一个Tooltip)
- 只有方块参与的配方无法添加到书签和设为默认配方(只有隐形的输入输出或者没写)
- 在含有炼药锅的配方中无法查看炼药锅中输入输出的流体是怎么合成的有什么用途(根本没把流体加进输入输出)
- ......(未完待续)  

 > 这个附属干了什么:
* Mixin了本体中大多数的Category
* 如果有输入或输出方块 会在配方的左右两侧以物品的形式添加这些方块(能查看该方块的合成或用途)
* 允许添加只有方块参与的配方到书签
* 在含有炼药锅都配方左右两侧添加了该炼药锅需要或输出的流体
* ......(还在写)

## 警告
* 此附属仍在开发, 目前始终使用铁砧工艺的未发布的构建版本(1.5.+), 意味着在使用此附属时应尽量使用这些未发布的构建版本
* 待铁砧工艺发布新的发布版本后会使用新的发布版本进行开发
* 反正就是还在写, 本体的配方重构的问题还没修完, 意味着这个附属还在写且还有很多问题
* 按理说这个附属的前置除了本体还有JEI, 但是没把JEI写进依赖, 所以不装JEI也能让游戏跑起来, 但是有什么问题没测过

## 许可证
本项目采用 LGPL-3.0 许可证，详见 [LICENSE](LICENSE) 文件
