// DataUtils.js
// Date formatting utility
export const formatDate = date => {
  const options = {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    hour12: true,
  };
  return new Date(date).toLocaleDateString(undefined, options);
};

export const relativeDateFormat = fromDate => {
  const now = new Date();
  const diffMs = now - new Date(fromDate);
  const diffSecs = Math.floor(diffMs / 1000);
  const diffMins = Math.floor(diffSecs / 60);
  const diffHrs = Math.floor(diffMins / 60);
  const diffDays = Math.floor(diffHrs / 24);

  if (diffDays > 0) return `${diffDays} days`;
  if (diffHrs > 0) return `${diffHrs} hours`;
  if (diffMins > 0) return `${diffMins} mins`;
  return `${diffSecs} secs`;
};
