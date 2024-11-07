import mitt from "mitt";

import Nwk from "@/types/Nwk";

interface Events {
    assignedNwk: Nwk;
    unassignedNwk: Nwk;
    nwkCreated: unknown;
    nwkDeleted: unknown;
    praktikumsstelleUpdated: unknown;
}

const emitter = mitt<Events>();

export default emitter;
