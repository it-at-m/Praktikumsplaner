import mitt from "mitt";

export type Eventtypes =
  | "assignedNwk"
  | "unassignedNwk"
  | "nwkCreated"
  | "nwkDeleted";

const emitter = mitt<Record<Eventtypes, unknown>>();

export default emitter;
