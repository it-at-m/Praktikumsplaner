import {
    ComputedOptions,
    ComponentOptionsMixin,
    MethodOptions,
} from "vue/types/v3-component-options";
import { ExtractPropTypes, ExtractDefaultPropTypes } from "vue";
import { EmitsOptions } from "vue/types/v3-setup-context";
import type { DefineComponent } from "vue";
import type { Wrapper } from "@vue/test-utils";

// This method fixes the issue with the frontend tests in this pipeline: https://github.com/it-at-m/Praktikumsplaner/actions/runs/6655108043/job/18084611498
// The solution is inspired by the comments in this issue: https://github.com/vuejs/vue-test-utils/issues/2026#issuecomment-1429963862
declare module "@vue/test-utils" {
    export function mount<
        PropsOrPropOptions = object,
        RawBindings = object,
        D = object,
        C extends ComputedOptions = ComputedOptions,
        M extends MethodOptions = MethodOptions,
        Mixin extends ComponentOptionsMixin = ComponentOptionsMixin,
        Extends extends ComponentOptionsMixin = ComponentOptionsMixin,
        E extends EmitsOptions = Record<string, any>,
        EE extends string = string,
        Props = Readonly<ExtractPropTypes<PropsOrPropOptions>>,
        Defaults extends object = ExtractDefaultPropTypes<PropsOrPropOptions>
    >(
        component: DefineComponent<
            PropsOrPropOptions,
            RawBindings,
            D,
            C,
            M,
            Mixin,
            Extends,
            E,
            EE,
            Props,
            Defaults
        >,
        options?: any
    ): Wrapper<any>;

    // Component declared with defineComponent
    export function shallowMount<
        PropsOrPropOptions = object,
        RawBindings = object,
        D = object,
        C extends ComputedOptions = ComputedOptions,
        M extends MethodOptions = MethodOptions,
        Mixin extends ComponentOptionsMixin = ComponentOptionsMixin,
        Extends extends ComponentOptionsMixin = ComponentOptionsMixin,
        E extends EmitsOptions = Record<string, any>,
        EE extends string = string,
        Props = Readonly<ExtractPropTypes<PropsOrPropOptions>>,
        Defaults extends object = ExtractDefaultPropTypes<PropsOrPropOptions>
    >(
        component: DefineComponent<
            PropsOrPropOptions,
            RawBindings,
            D,
            C,
            M,
            Mixin,
            Extends,
            E,
            EE,
            Props,
            Defaults
        >,
        options?: any
    ): Wrapper<any>;
}
