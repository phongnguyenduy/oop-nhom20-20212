CREATE TABLE public.ba_me_mang_thai (
    id_3 bigint NOT NULL,
    ma_dinh_danh_y_te text,
    can_nang integer NOT NULL,
    lan_kham_gan_nhat text,
    tham_kham_sap_toi text
);
CREATE SEQUENCE public.ba_me_mang_thai_id_3_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.ba_me_mang_thai_id_3_seq OWNED BY public.ba_me_mang_thai.id_3;
CREATE TABLE public.dang_nhap (
    user_id bigint NOT NULL,
    ten_dang_nhap text NOT NULL,
    email text NOT NULL,
    mat_khau text NOT NULL
);
CREATE SEQUENCE public.dang_nhap_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.dang_nhap_user_id_seq OWNED BY public.dang_nhap.user_id;
CREATE TABLE public.lich_tiem_chung (
    id integer NOT NULL,
    "time" date NOT NULL,
    address text NOT NULL,
    name text NOT NULL,
    amount integer NOT NULL
);
CREATE TABLE public.so_lieu_tre_em (
    fid integer NOT NULL,
    ma_dinh_danh_y_te text NOT NULL,
    thang_tuoi integer NOT NULL,
    chieu_cao double precision NOT NULL,
    can_nang double precision NOT NULL
);
CREATE TABLE public.thong_tin_ca_nhan (
    ma_dinh_danh_y_te text NOT NULL,
    ten text NOT NULL,
    can_cuoc_cong_dan text,
    gioi_tinh text NOT NULL,
    ngay_sinh date NOT NULL,
    dia_chi text NOT NULL,
    so_dien_thoai text NOT NULL,
    email text NOT NULL,
    tinh_trang_tiem_vaccine text
);
CREATE TABLE public.thong_tin_tiem_chung (
    id_1 text NOT NULL,
    ma_dinh_danh_y_te text NOT NULL,
    loai_vaccine text NOT NULL,
    ngay_tiem text NOT NULL,
    so_lo_vaccine text NOT NULL,
    tinh_trang_sau_tiem text NOT NULL,
    so_mui_tiem integer NOT NULL
);
CREATE SEQUENCE public.thong_tin_tiem_chung_id_1_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.thong_tin_tiem_chung_id_1_seq OWNED BY public.thong_tin_tiem_chung.id_1;
CREATE TABLE public.thong_tin_tre_em (
    ma_dinh_danh_y_te text NOT NULL,
    ho_ten text NOT NULL,
    ngay_sinh date NOT NULL,
    gioi_tinh text NOT NULL,
    bo text NOT NULL,
    me text NOT NULL
);
ALTER TABLE ONLY public.ba_me_mang_thai ALTER COLUMN id_3 SET DEFAULT nextval('public.ba_me_mang_thai_id_3_seq'::regclass);
--ALTER TABLE public.ba_me_mang_thai ALTER COLUMN id_3 DROP DEFAULT;
ALTER TABLE ONLY public.dang_nhap ALTER COLUMN user_id SET DEFAULT nextval('public.dang_nhap_user_id_seq'::regclass);
--ALTER TABLE public.dang_nhap ALTER COLUMN user_id DROP DEFAULT;
ALTER TABLE ONLY public.thong_tin_tiem_chung ALTER COLUMN id_1 SET DEFAULT nextval('public.thong_tin_tiem_chung_id_1_seq'::regclass);
--ALTER TABLE public.thong_tin_tiem_chung ALTER COLUMN id_1 DROP DEFAULT;
/*
COPY public.ba_me_mang_thai (id_3, ma_dinh_danh_y_te, can_nang, lan_kham_gan_nhat, tham_kham_sap_toi) FROM stdin;
COPY public.dang_nhap (user_id, ten_dang_nhap, email, mat_khau) FROM stdin;
COPY public.lich_tiem_chung (id, "time", address, name, amount) FROM stdin;
COPY public.so_lieu_tre_em (fid, ma_dinh_danh_y_te, thang_tuoi, chieu_cao, can_nang) FROM stdin;
COPY public.thong_tin_ca_nhan (ma_dinh_danh_y_te, ten, can_cuoc_cong_dan, gioi_tinh, ngay_sinh, dia_chi, so_dien_thoai, email, tinh_trang_tiem_vaccine) FROM stdin;
COPY public.thong_tin_tiem_chung (id_1, ma_dinh_danh_y_te, loai_vaccine, ngay_tiem, so_lo_vaccine, tinh_trang_sau_tiem, so_mui_tiem) FROM stdin;
COPY public.thong_tin_tre_em (ma_dinh_danh_y_te, ho_ten, ngay_sinh, gioi_tinh, bo, me) FROM stdin;
SEQUENCE SET SELECT pg_catalog.setval('public.ba_me_mang_thai_id_3_seq', 22, true);
SEQUENCE SET SELECT pg_catalog.setval('public.dang_nhap_user_id_seq', 6, true);
SEQUENCE SET SELECT pg_catalog.setval('public.thong_tin_tiem_chung_id_1_seq', 1, false);
*/
ALTER TABLE ONLY public.ba_me_mang_thai
    ADD CONSTRAINT ba_me_mang_thai_pk PRIMARY KEY (id_3);
ALTER TABLE ONLY public.dang_nhap
    ADD CONSTRAINT dang_nhap_pk PRIMARY KEY (user_id);
ALTER TABLE ONLY public.lich_tiem_chung
    ADD CONSTRAINT lich_tiem_chung_pk PRIMARY KEY (id);
ALTER TABLE ONLY public.so_lieu_tre_em
    ADD CONSTRAINT numeral_pk PRIMARY KEY (fid);
ALTER TABLE ONLY public.thong_tin_ca_nhan
    ADD CONSTRAINT thong_tin_nguoi_benh_pk PRIMARY KEY (ma_dinh_danh_y_te);
ALTER TABLE ONLY public.thong_tin_tiem_chung
    ADD CONSTRAINT thong_tin_tiem_chung_pk PRIMARY KEY (id_1);
ALTER TABLE ONLY public.thong_tin_tre_em
    ADD CONSTRAINT thong_tin_tre_em_pk PRIMARY KEY (ma_dinh_danh_y_te);
ALTER TABLE ONLY public.ba_me_mang_thai
    ADD CONSTRAINT ba_me_mang_thai_thong_tin_ca_nhan_ma_dinh_danh_y_te_fk FOREIGN KEY (ma_dinh_danh_y_te) REFERENCES public.thong_tin_ca_nhan(ma_dinh_danh_y_te);
ALTER TABLE ONLY public.so_lieu_tre_em
    ADD CONSTRAINT numeral_thong_tin_tre_em_ma_dinh_danh_y_te_fk FOREIGN KEY (ma_dinh_danh_y_te) REFERENCES public.thong_tin_tre_em(ma_dinh_danh_y_te);
ALTER TABLE ONLY public.thong_tin_tiem_chung
    ADD CONSTRAINT thong_tin_tiem_chung_thong_tin_ca_nhan_ma_dinh_danh_y_te_fk FOREIGN KEY (ma_dinh_danh_y_te) REFERENCES public.thong_tin_ca_nhan(ma_dinh_danh_y_te);
ALTER TABLE ONLY public.thong_tin_tre_em
    ADD CONSTRAINT thong_tin_tre_em_thong_tin_ca_nhan_ma_dinh_danh_y_te_fk FOREIGN KEY (ma_dinh_danh_y_te) REFERENCES public.thong_tin_ca_nhan(ma_dinh_danh_y_te);