import mitt from "mitt";

import Nwk from "@/types/Nwk";

type Events = {
    assignedNwk: Nwk;
    unassignedNwk: Nwk;
    nwkCreated: void;
    nwkDeleted: void;
};

const emitter = mitt<Events>();

export default emitter;
