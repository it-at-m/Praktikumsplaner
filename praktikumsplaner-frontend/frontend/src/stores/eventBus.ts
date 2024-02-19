import mitt from "mitt";

import Nwk from "@/types/Nwk";

type Events = {
    assignedNwk: Nwk;
    unassignedNwk: Nwk;
};

const emitter = mitt<Events>();

export default emitter;
