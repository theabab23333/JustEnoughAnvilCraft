# JustEnoughAnvilCraft

[简体中文](readme.md) || [English](readme.en.md)  
[![CI for Mod](https://github.com/theabab23333/JustEnoughAnvilCraft/actions/workflows/ci.yml/badge.svg)](https://github.com/theabab23333/JustEnoughAnvilCraft/actions/workflows/ci.yml)  
[![Pull Request Check](https://github.com/theabab23333/JustEnoughAnvilCraft/actions/workflows/pull_request.yml/badge.svg)](https://github.com/theabab23333/JustEnoughAnvilCraft/actions/workflows/pull_request.yml)

## JustEnoughAnvilCraft is an addon for [AnvilCraft](https://github.com/AnvilCraft/AnvilCraft) that enhances the display of AnvilCraft in JEI

> Disadvantages of the original recipes in JEI:
- In recipes involving blocks, you cannot view the synthesis recipes or uses of the input/output blocks (they are just tooltips)
- Recipes that only involve blocks cannot be bookmarked or set as default recipes (only have hidden inputs/outputs or nothing written)
- In recipes involving cauldrons, you cannot see how the fluids input/output in the cauldron are synthesized or what their uses are (fluids are not added to inputs/outputs at all)
- ... (to be continued)

> What this addon does:
* Mixins most Categories of the original mod
* If there are input or output blocks, it adds these blocks in item form on the left and right sides of the recipe (allowing you to view synthesis or usage)
* Allows recipes that only involve blocks to be bookmarked
* Adds the fluids required or output by the cauldron in recipes involving cauldrons to the left and right sides
* ... (still writing)

## Warning
* This addon is still under development and always uses unpublished builds of AnvilCraft (1.5.+), which means you should try to use these unpublished builds when using this addon
* After AnvilCraft releases a new stable version, development will switch to using the new stable version
* In short, it's still being written, and the recipe refactoring issues in the original mod haven't been fully fixed, meaning this addon is still in development and has many issues
* Technically, this addon's prerequisites include JEI in addition to the original mod, but JEI is not written in the dependencies, so the game can still run without JEI, but what problems might occur hasn't been tested

## License
This project is licensed under LGPL-3.0, see [LICENSE](LICENSE) file for details.