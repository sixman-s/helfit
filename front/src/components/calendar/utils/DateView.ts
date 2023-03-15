export const DateView = (current: Date) => {
  let year = current.getFullYear().toString();
  let month = (+current.getMonth() + 1).toString().padStart(2, '0');
  let day = current.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
};
