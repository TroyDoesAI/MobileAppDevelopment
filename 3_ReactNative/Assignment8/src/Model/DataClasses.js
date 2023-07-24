export class Workspace {
  constructor(id, name, channels) {
    this.id = id;
    this.name = name;
    this.channels = channels;
  }

  get uniquePosters() {
    const members = this.channels.flatMap(channel =>
      channel.messages.map(message => message.member.id),
    );
    return new Set(members).size;
  }

  get mostRecentMessage() {
    return Math.max(...this.channels.map(channel => channel.mostRecentMessage));
  }
}

export class Channel {
  constructor(id, name, messages) {
    this.id = id;
    this.name = name;
    this.messages = messages;
  }

  get uniquePosters() {
    const members = this.messages.map(message => message.member.id);
    return new Set(members).size;
  }

  get mostRecentMessage() {
    return Math.max(...this.messages.map(message => message.posted));
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
