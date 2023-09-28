/**
 * Mit dem SaveLeaveComposable kann ein Datenverlust durch ungewolltest Navigieren verhindert werden.
 *
 * Nimmt eine Funktion `isDirty()` entgegen, in der festgelegt werden kann,
 * ob sicher navigiert werden kann oder eine Rückfrage an den Nutzer stattfinden soll.
 * Diese Rückfrage kann z.B. über einen Dialog gelöst werden. Hierzu bietet das SaveLeaveComposable
 * ein `saveLeaveDialog` Flag an. Für genererische Dialoge bietet das SaveLeaveComposable bereits Titel und
 * Text mit an.
 *
 * Mit dem Aufruf von `leave()` oder `cancel()` kann die Entscheidung des Nutzers ausgeführt werden.
 */
import { onBeforeRouteLeave } from "vue-router/composables";
import { NavigationGuardNext, Route } from "vue-router";
import { ref } from "vue";

export function useSaveLeave(isDirty: () => boolean) {
    const saveLeaveDialogTitle = ref("Ungespeicherte Änderungen");
    const saveLeaveDialogText = ref(
        "Es sind ungespeicherte Änderungen vorhanden. Wollen Sie die Seite verlassen?"
    );
    const saveLeaveDialog = ref(false);
    const isSave = ref(false);

    const nextRoute = ref<NavigationGuardNext | null>(null);

    onBeforeRouteLeave((to: Route, from: Route, next: NavigationGuardNext) => {
        if (isDirty() && !isSave.value) {
            saveLeaveDialog.value = true;
            nextRoute.value = next;
        } else {
            saveLeaveDialog.value = false;
            next();
        }
    });

    function cancel(): void {
        saveLeaveDialog.value = false;
        if (nextRoute.value != null) {
            nextRoute.value(false);
        }
    }

    function leave(): void {
        if (nextRoute.value != null) {
            nextRoute.value();
        }
    }

    return {
        saveLeaveDialogTitle,
        saveLeaveDialogText,
        saveLeaveDialog,
        isSave,
        nextRoute,
        cancel,
        leave,
    };
}
