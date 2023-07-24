// Utils/ElapsedTimeFormatter.js
export const formatElapsedTime = mostRecentDate => {
  const now = new Date();
  const diff = now - mostRecentDate;

  const seconds = Math.floor(diff / 1000);
  if (seconds < 60) {
    return `${seconds} secs`;
  }
  const minutes = Math.floor(seconds / 60);
  if (minutes < 60) {
    return `${minutes} mins`;
  }
  const hours = Math.floor(minutes / 60);
  if (hours < 24) {
    return `${hours} hours`;
  }
  const days = Math.floor(hours / 24);
  return `${days} days`;
};
