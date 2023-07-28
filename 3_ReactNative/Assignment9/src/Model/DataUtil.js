// DataUtils.js

// Date formatting utility
export const formatDate = date => {
  const dateOptions = {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
  };

  const timeOptions = {
    hour: 'numeric',
    minute: 'numeric',
    hour12: true,
  };

  const formattedDate = new Date(date).toLocaleDateString(
    undefined,
    dateOptions,
  );
  const formattedTime = new Date(date).toLocaleTimeString(
    undefined,
    timeOptions,
  );

  return `${formattedDate} at ${formattedTime}`;
};
