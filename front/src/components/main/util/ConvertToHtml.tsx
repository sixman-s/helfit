const escapeMap = {
  '&lt;': '<',
  '&#12296;': '<',
  '&gt;': '>',
  '&#12297;': '>',
  '&amp;': '&',
  '&quot;': '"',
  '&#x27;': "'"
};
const pattern = /&(lt|gt|amp|quot|#x27|#12296|#12297);/g;
const reg = /<[^>]*>?/g;

export const ConvertToHtml = (text) =>
  text
    .replace(pattern, (match, entity) => escapeMap[`&${entity};`] || match)
    .replace(reg, '');
