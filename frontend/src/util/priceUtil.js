export const formatARS = (value) => {
    if (typeof value !== 'number') return '???';
    return value.toLocaleString('es-AR', { style: 'currency', currency: 'ARS', minimumFractionDigits: 2 });
};