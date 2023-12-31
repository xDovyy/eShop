PGDMP                 
        {           eShop    15.2    15.2                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    50827    eShop    DATABASE     �   CREATE DATABASE "eShop" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United Kingdom.1252';
    DROP DATABASE "eShop";
                postgres    false            �            1259    50988 
   categories    TABLE     M   CREATE TABLE public.categories (
    name character varying(255) NOT NULL
);
    DROP TABLE public.categories;
       public         heap    postgres    false            �            1259    50993    items    TABLE     ~  CREATE TABLE public.items (
    id uuid NOT NULL,
    created_at timestamp without time zone,
    description character varying(255),
    is_deleted boolean NOT NULL,
    name character varying(255) NOT NULL,
    price real NOT NULL,
    quantity integer NOT NULL,
    updated_at timestamp without time zone,
    category_name character varying(255) NOT NULL,
    seller_id uuid
);
    DROP TABLE public.items;
       public         heap    postgres    false            �            1259    51000    orders    TABLE     N  CREATE TABLE public.orders (
    id uuid NOT NULL,
    address character varying(255),
    created_at timestamp without time zone,
    is_deleted boolean NOT NULL,
    quantity integer NOT NULL,
    status character varying(255),
    total_price real,
    updated_at timestamp without time zone,
    item_id uuid,
    user_id uuid
);
    DROP TABLE public.orders;
       public         heap    postgres    false            �            1259    51007    users    TABLE     �  CREATE TABLE public.users (
    id uuid NOT NULL,
    address character varying(255),
    created_at timestamp without time zone,
    email character varying(255) NOT NULL,
    is_deleted boolean NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    phone numeric(19,2),
    role character varying(255),
    surname character varying(255) NOT NULL,
    updated_at timestamp without time zone
);
    DROP TABLE public.users;
       public         heap    postgres    false                      0    50988 
   categories 
   TABLE DATA           *   COPY public.categories (name) FROM stdin;
    public          postgres    false    214   �                 0    50993    items 
   TABLE DATA           �   COPY public.items (id, created_at, description, is_deleted, name, price, quantity, updated_at, category_name, seller_id) FROM stdin;
    public          postgres    false    215   �                 0    51000    orders 
   TABLE DATA           �   COPY public.orders (id, address, created_at, is_deleted, quantity, status, total_price, updated_at, item_id, user_id) FROM stdin;
    public          postgres    false    216   �                  0    51007    users 
   TABLE DATA           }   COPY public.users (id, address, created_at, email, is_deleted, name, password, phone, role, surname, updated_at) FROM stdin;
    public          postgres    false    217   s!       q           2606    50992    categories categories_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (name);
 D   ALTER TABLE ONLY public.categories DROP CONSTRAINT categories_pkey;
       public            postgres    false    214            s           2606    50999    items items_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.items
    ADD CONSTRAINT items_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.items DROP CONSTRAINT items_pkey;
       public            postgres    false    215            u           2606    51006    orders orders_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_pkey;
       public            postgres    false    216            w           2606    51015 "   users uk_6dotkott2kjsp8vw4d0m25fb7 
   CONSTRAINT     ^   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);
 L   ALTER TABLE ONLY public.users DROP CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7;
       public            postgres    false    217            y           2606    51013    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    217            |           2606    51026 "   orders fk247nnxschdfm8lre0ssvy3k1r    FK CONSTRAINT     �   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk247nnxschdfm8lre0ssvy3k1r FOREIGN KEY (item_id) REFERENCES public.items(id);
 L   ALTER TABLE ONLY public.orders DROP CONSTRAINT fk247nnxschdfm8lre0ssvy3k1r;
       public          postgres    false    216    215    3187            }           2606    51031 "   orders fk32ql8ubntj5uh44ph9659tiih    FK CONSTRAINT     �   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES public.users(id);
 L   ALTER TABLE ONLY public.orders DROP CONSTRAINT fk32ql8ubntj5uh44ph9659tiih;
       public          postgres    false    3193    216    217            z           2606    51016 !   items fk7lx6ccu3hx1pcxos0kif0803y    FK CONSTRAINT     �   ALTER TABLE ONLY public.items
    ADD CONSTRAINT fk7lx6ccu3hx1pcxos0kif0803y FOREIGN KEY (category_name) REFERENCES public.categories(name);
 K   ALTER TABLE ONLY public.items DROP CONSTRAINT fk7lx6ccu3hx1pcxos0kif0803y;
       public          postgres    false    214    3185    215            {           2606    51021 !   items fksm9ro5ntn6yaav2m7ydato0fc    FK CONSTRAINT     �   ALTER TABLE ONLY public.items
    ADD CONSTRAINT fksm9ro5ntn6yaav2m7ydato0fc FOREIGN KEY (seller_id) REFERENCES public.users(id);
 K   ALTER TABLE ONLY public.items DROP CONSTRAINT fksm9ro5ntn6yaav2m7ydato0fc;
       public          postgres    false    215    3193    217               F   x�+(J��O�/�J�I�.)����N�J*�,��;2]�$59,rd}Qb^vfb&WbiI~n~Rf����� �1z         �  x��V;s�6��_�����@�:�r�$���E�� �n���ܻt�I��3q�������$�9�&i�<H�~�=���8g���,2�U)�L��*��0:Jx�2�3��K�.�<.�Lu����p�=�4}Y��q��PO8�z�n�$�۷]������<!�ڃ���.�X�e��.~�<x����M�w����w��������-_U�VC��G"��Y���W��\�,q9�D&57�YJ���Ԣ.�;ҙ�Ε9�
'�L*ڔ£*T�&���:�C.�,.�\K=E�Vn�G0sݸq�Y_g/5Lnh{ji��p�&׀�X�)~
χ�t�� �3x��[����+'��b8��^�v�1<�xKg��o��tp>�	�8�+C���n�:G����M�%TsӜZp��AHy^��7��C%�g#��2�޺�G|��j׸z���x?���,MK�r-&��,GU0]`��V�uq��^�$�	�JG/=�����!�v)�mOv3�5H��`��<��f�q�6�[���n���[��g����n�&
8��9~�� T��B��7�qr-.�&��v�QE״���'ǟP�P�C�-N7��ht��8�~�|W�C���޾^�`5��#���MMD��gK�����l��_���/h2Ǉ�1ؽk�t�@Q��(�aZf���HF�+��?�4QyΫ�)t)�R�����HWZS)e���)��"ΈZ�*��ݽ_�#5��u�q�7ļ3�=�||�\����w9\.�Oph^���{�m?�ALQZ�̬c��Ȥ����e5*�Yn*{���
�a�H�dFuW�ѤIUZ�CQ�d�uL�'Y��m�v� ��*���|�5Mc֓��W1o<4~���p�w4���N�I�����s��گ�Fx�$�������G���Ó�ȹ	��Ly"e�����%�'e\DB�9ODZV���l�e&U��t�(E���
���XfR�"zr����p��xrAs�F�����/'��o0p�W
�~C�ǏIx]�W+ie�7�b ���� ݓ�[���j׸�y^:��6n�������CQ����n���:7��[�m7m?��%<T�i�{0��O'�a�)����Z@��?E2��?@�Z�5�)S.+��ӊFeLTBi�%i4?�:_��/I@_d%�u[��8�u?P�lߒ˻��Z��0�B6�[�$�,�t��4���$xM��	4����So�Di���p�`�D���4��ݣ���sp���,��I�{!�W��+f���"hr��h�L��S|��8ӉL��yq��ao�1<E,v���e?�9�>1HۛO�d�-
XS|�t�'����?�l���WS<Ql�|���vn�-81|O֎@)F��F}*�𤀷�M_�/H�1�&^��۾1�,"��3��3mo|so�eV����*F֔�B�R��i�i-9_J����^Dq8'�T�_SPj�;B�WP��(�t�         {   x���!��V�~� l�`/�J�%�%s�P�Z�{0���\��sx�?�Q����t�^���&�K���8�Rӝ0ߊa�Ɍe�H�3.t���'��yN�8W��$�Ӫ������$�         k  x�u��r�F���Sx1˴��ū0�67Aͦ[��&�_$O��2�f2�L��R���J�w���IH�!8e%��
`I���0����_��� (��P����PA!
�|����w�Nk}�|$A���z|�����Q���,�R�"S3S㰘5_�f�TU�S�sl�E��}�۾?��\yzl]}��*�^� xQ����h����K��H�P`�K$6���k�^
!dD�`����q%�q����f��5Z�&Ei�y-�kکN__ߧU��;y�|���X��^�\L������^��ۣ����}��&S\�9�Hk ��@2�eLFa���;�C9�2X��\Ѷ�l��	���ixܑS��yw8�����{t<M&�89�,;S��S�bN#//��º3�"YD(���3l��H[�1��4��;�����<ص�m?�:�5�e�]��N��\3�T��A�l�7���N���3*ړ��h���Ｕ�q�E�ŉFC{d��a|^�B��ȇ����*,I�vJgW�_�<��R��_�}GB/�%�v�~_�i�E萌OaS>-D��t�ާ&��9
a^m��[ӒИ Ƅ�!��� 8w� �;LC�(E<��+�����8#,Oik�^��v:��xRN�tLdi������j�Z�U�a�3�B�V��M "��X����>��IǨ��G��w��	�9��.������<� �"���x�>t>Ǜ��{��i��9�x(�6��Is:�lzY*3ݻ 7�����S��UBx ����H�	PJ��'�B�Q��9��_�y�j��������I�frx�m7��?�-
+o���z�Z4�x}���M���BLʛ�Э�E~�ĘĊ��:_��Bc���6�j���B��C�}�+���o����n��b���W��sT��B�����][~v��%ܭ��78)�GީOa1\�fO��`Ykq�c����A�t�*�E��\@�q���j�����W�a��HR�Jd�Pc)ʡF	��6���c�������E���<Ћw]./��"��!q
MjW�<JIa�:���U�yw~�\�A�%w/Y��p;��'�{���d$�s�o��777� .!     