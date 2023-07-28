// Model/DataClasses.js
export class Workspace {
  constructor(id, name, channels) {
    this.id = id;
    this.name = name;
    this.channels = channels;
  }
}

export class Channel {
  constructor(id, name, messageCount) {
    this.id = id;
    this.name = name;
    this._messageCount = messageCount; // Use underscore to denote private/internal variable
  }

  get messageCount() {
    return this._messageCount; // Use the internal variable here
  }
}

export class Message {
  constructor(id, content, posted, member) {
    this.id = id;
    this.content = content;
    this.posted = new Date(posted);
    this.member = member;
  }
}

export class Member {
  constructor(id, name) {
    this.id = id;
    this.name = name;
  }
}
